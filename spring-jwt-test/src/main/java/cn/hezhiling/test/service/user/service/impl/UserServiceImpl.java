package cn.hezhiling.test.service.user.service.impl;

import cn.hezhiling.test.service.otp.dao.OtpMapper;
import cn.hezhiling.test.service.otp.dto.Otp;
import cn.hezhiling.test.service.user.dao.UserMapper;
import cn.hezhiling.test.common.util.GenerateCodeUtil;
import cn.hezhiling.test.service.user.dto.User;
import cn.hezhiling.test.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 09:54:23
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private OtpMapper otpMapper;

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    /**
     * 授权
     *
     * @param user {@link User}
     */
    @Override
    public void auth(User user) {
        User userInDB = userMapper.selectByPrimaryKey(user.getUsername());
        if(ObjectUtils.isEmpty(userInDB)) {
            throw new BadCredentialsException("依据用户名找不到用户");
        }
        if (passwordEncoder.matches(user.getPassword(), userInDB.getPassword())) {
            renewOtp(user);
        } else {
            throw new BadCredentialsException("用户名密码错误");
        }
    }

    /**
     * 检验授权码
     *
     * @param otpToValidate {@link Otp}
     * @return 如果授权码正确则返回true，否则false
     */
    @Override
    public boolean check(Otp otpToValidate) {
        Otp otp = otpMapper.selectByPrimaryKey(otpToValidate.getUsername());
        if (ObjectUtils.isEmpty(otp)) {
            return false;
        }
        return otpToValidate.getCode().equals(otp.getCode());
    }

    void renewOtp(User user) {
        String code = GenerateCodeUtil.generateCode();
        Otp userInDB = otpMapper.selectByPrimaryKey(user.getUsername());
        Otp otp = new Otp();
        if (!ObjectUtils.isEmpty(userInDB)) {
            otp.setCode(code);
            otpMapper.updateByPrimaryKey(otp);
        } else {
            otp.setUsername(user.getUsername());
            otp.setCode(code);
            otpMapper.insert(otp);
        }
    }

}
