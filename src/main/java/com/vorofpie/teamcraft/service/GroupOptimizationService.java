package com.vorofpie.teamcraft.service;

import com.vorofpie.teamcraft.model.*;
import com.vorofpie.teamcraft.repository.ProgrammerRepository;
import com.vorofpie.teamcraft.repository.ProjectRepository;
import com.vorofpie.teamcraft.repository.ThresholdRepository;
import com.vorofpie.teamcraft.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class GroupOptimizationService {

    private static final int NUM_GENERATIONS = 50;
    private static final double MUTATION_RATE = 0.2;
    private static final double CROSSOVER_RATE = 0.8;

    private final ProgrammerRepository programmerRepository;
    private final ProjectRepository projectRepository;
    private final ThresholdRepository thresholdRepository;

    public GroupOptimizationService(ProgrammerRepository programmerRepository, ProjectRepository projectRepository, ThresholdRepository thresholdRepository) {
        this.programmerRepository = programmerRepository;
        this.projectRepository = projectRepository;
        this.thresholdRepository = thresholdRepository;
    }

    public Group optimizeGroup(Project project, int groupSize, int populationSize) {
        List<Programmer> programmers = programmerRepository.getAllProgrammers();
        System.out.println("Initial number of programmers: " + programmers.size());

        Map<Technology, Threshold> thresholds = getThresholdsForProject(project);

        // Создаем начальную популяцию
        List<List<Programmer>> population = initializePopulation(programmers, groupSize, populationSize, thresholds);
        System.out.println("Initial population size: " + population.size());

        // Эволюция
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            System.out.println("Generation " + (i + 1) + " - Population size: " + population.size());
            population = evolvePopulation(population, thresholds, populationSize, groupSize);
            ;
        }

        // Выбираем лучшую группу
        List<Programmer> bestProgrammers = selectBestSolution(population, thresholds);
        System.out.println("Best group size: " + bestProgrammers.size());

        // Создаем одну группу
        Group bestGroup = new Group();
        bestGroup.setGroupName("Оптимальная группа");
        bestGroup.setProject(project);
        bestGroup.setProgrammers(new HashSet<>(bestProgrammers));

        // Сохраняем в БД
        saveGroupToDatabase(bestGroup);
        return bestGroup;
    }

    private List<List<Programmer>> initializePopulation(List<Programmer> programmers, int groupSize, int populationSize, Map<Technology, Threshold> thresholds) {
        List<List<Programmer>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Programmer> randomGroup = selectRandomGroup(programmers, groupSize, thresholds);
            population.add(randomGroup);
        }

        System.out.println("Population initialized with " + population.size() + " groups.");
        return population;
    }

    private List<Programmer> selectRandomGroup(List<Programmer> programmers, int groupSize, Map<Technology, Threshold> thresholds) {
        Set<Programmer> selectedGroup = new HashSet<>(); // Используем HashSet для уникальности программистов в группе

        while (selectedGroup.size() < groupSize) {
            // Генерируем случайного программиста
            Programmer randomProgrammer = programmers.get(new Random().nextInt(programmers.size()));

            // Проверяем, удовлетворяет ли он порогам и если он ещё не добавлен в группу
            if (meetsThresholds(randomProgrammer, thresholds)) {
                selectedGroup.add(randomProgrammer); // HashSet гарантирует уникальность
            }
        }

        System.out.println("Selected group size: " + selectedGroup.size());
        return new ArrayList<>(selectedGroup); // Преобразуем HashSet в List для дальнейшей обработки
    }

    private boolean meetsThresholds(Programmer programmer, Map<Technology, Threshold> thresholds) {
        for (SkillLevel skill : programmer.getSkillLevels()) {
            Threshold threshold = thresholds.get(skill.getTechnology());
            if (threshold != null && skill.getLevel() < threshold.getMinLevel()) {
                return false; // Программист не проходит по минимальному уровню
            }
        }
        return true; // Все навыки выше порога
    }




    private List<List<Programmer>> evolvePopulation(List<List<Programmer>> population, Map<Technology, Threshold> thresholds, int populationSize, int groupSize) {
        List<List<Programmer>> newPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Programmer> parent1 = selectRandomSolution(population, thresholds);
            List<Programmer> parent2 = selectRandomSolution(population, thresholds);
            List<Programmer> offspring = crossover(parent1, parent2, groupSize);  // Передаем groupSize
            mutate(offspring);
            newPopulation.add(offspring);
        }
        System.out.println("New population created with " + newPopulation.size() + " groups.");
        return newPopulation;
    }


    private List<Programmer> crossover(List<Programmer> parent1, List<Programmer> parent2, int groupSize) {
        if (Math.random() > CROSSOVER_RATE) {
            return new ArrayList<>(parent1); // Если скрещивание не происходит, просто возвращаем родителя 1
        }

        Set<Programmer> childSet = new HashSet<>(); // Используем Set для уникальности программистов в дочерней группе
        int crossoverPoint = Math.min(parent1.size(), parent2.size()) / 2;

        // Первая половина от родителя 1
        childSet.addAll(parent1.subList(0, crossoverPoint));

        // Вторая половина от родителя 2
        childSet.addAll(parent2.subList(crossoverPoint, parent2.size()));

        // Если размер дочерней группы меньше groupSize, дополняем её оставшимися программистами
        if (childSet.size() < groupSize) {
            // Добавляем программистов из родительских групп, чтобы достичь нужного размера
            addRemainingProgrammers(childSet, parent1, parent2, groupSize);
        }

        // Преобразуем Set обратно в List
        List<Programmer> childList = new ArrayList<>(childSet);
        System.out.println("Crossover done, child size: " + childList.size());
        return childList;
    }


    private void addRemainingProgrammers(Set<Programmer> childSet, List<Programmer> parent1, List<Programmer> parent2, int groupSize) {
        Random rand = new Random();

        // Собираем всех программистов из родителей, которых ещё нет в дочерней группе
        List<Programmer> allProgrammers = new ArrayList<>();
        allProgrammers.addAll(parent1);
        allProgrammers.addAll(parent2);

        // Убираем программистов, которые уже есть в childSet
        allProgrammers.removeAll(childSet);

        // Дополняем дочернюю группу до нужного размера
        while (childSet.size() < groupSize) {
            Programmer randomProgrammer = allProgrammers.get(rand.nextInt(allProgrammers.size()));
            childSet.add(randomProgrammer);
            allProgrammers.remove(randomProgrammer); // Убираем программиста, чтобы не добавлять его снова
        }
    }



    private void mutate(List<Programmer> programmers) {
        if (Math.random() > MUTATION_RATE) return;

        // Переставляем случайных программистов в группе
        Random rand = new Random();
        int index1 = rand.nextInt(programmers.size());
        int index2 = rand.nextInt(programmers.size());

        // Проверка, чтобы не менять местами одного и того же программиста
        while (index1 == index2) {
            index2 = rand.nextInt(programmers.size()); // Перегенерируем индекс 2, если они одинаковые
        }

        // Меняем местами программистов
        Collections.swap(programmers, index1, index2);
        System.out.println("Mutation done, group size: " + programmers.size());
    }

    private List<Programmer> selectRandomSolution(List<List<Programmer>> population, Map<Technology, Threshold> thresholds) {
        Random rand = new Random();
        List<Programmer> randomProgrammers = population.get(rand.nextInt(population.size()));
        return randomProgrammers.stream()
                .filter(p -> meetsThresholds(p, thresholds))
                .collect(Collectors.toList());
    }


    private List<Programmer> selectBestSolution(List<List<Programmer>> population, Map<Technology, Threshold> thresholds) {
        return population.stream()
                .max(Comparator.comparingDouble(programmers -> evaluateFitness(programmers, thresholds)))
                .orElse(population.get(0));
    }

    private double evaluateFitness(List<Programmer> programmers, Map<Technology, Threshold> thresholds) {
        double avgQualification = programmers.stream()
                .mapToDouble(p -> p.getSkillLevels().stream()
                        .mapToDouble(skill -> skill.getLevel() * skill.getTechnology().getRating())
                        .sum())
                .average().orElse(0.0);

        double minQualification = programmers.stream()
                .mapToDouble(p -> p.getSkillLevels().stream()
                        .mapToDouble(SkillLevel::getLevel)
                        .min().orElse(0.0))
                .min().orElse(0.0);

        if (minQualification < thresholds.values().stream().mapToDouble(Threshold::getMinLevel).min().orElse(0.0)) {
            return 0.0; // Если группа не соответствует минимальному порогу, её оценка равна 0
        }

        return avgQualification;
    }

    private Map<Technology, Threshold> getThresholdsForProject(Project project) {
        return thresholdRepository.findByProject(project).stream()
                .collect(Collectors.toMap(Threshold::getTechnology, t -> t));
    }

    private void saveGroupToDatabase(Group group) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(group);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
