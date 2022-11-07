package cn.hezhiling.test.service.workout.service;

import cn.hezhiling.test.service.workout.dto.WorkoutDTO;

import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-07-03 21:09:05
 */
public interface WorkoutService {

    void saveWorkout(WorkoutDTO workoutDTO);

    WorkoutDTO findWorkouts(Integer id);

    void deleteWorkout(Integer id);
}
