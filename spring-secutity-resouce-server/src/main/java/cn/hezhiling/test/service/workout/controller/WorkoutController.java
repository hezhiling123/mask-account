package cn.hezhiling.test.service.workout.controller;

import cn.hezhiling.test.service.workout.dto.WorkoutDTO;
import cn.hezhiling.test.service.workout.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-07-03 21:14:13
 */
@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @PostMapping("/")
    public void add(@RequestBody WorkoutDTO workoutDTO) {
        workoutService.saveWorkout(workoutDTO);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('AMDIN')")
    public WorkoutDTO findAll(@PathVariable Integer id) {
        return workoutService.findWorkouts(id);
    }

    @PostMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        workoutService.deleteWorkout(id);
    }
}
