package cn.hezhiling.test.service.workout.service.impl;

import cn.hezhiling.test.service.workout.dao.WorkoutMapper;
import cn.hezhiling.test.service.workout.dto.WorkoutDTO;
import cn.hezhiling.test.service.workout.service.WorkoutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-07-03 21:11:43
 */
@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Resource
    private WorkoutMapper workoutMapper;

    @Override
    @PreAuthorize("#workoutDTO.user == authentication.name and #oauth2.hasScope('fitnessapp')")
    public void saveWorkout(WorkoutDTO workoutDTO) {
        workoutMapper.insert(workoutDTO);
    }

    @Override
    public WorkoutDTO findWorkouts(Integer id) {
        return workoutMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteWorkout(Integer id) {
        workoutMapper.deleteByPrimaryKey(id);
    }
}
