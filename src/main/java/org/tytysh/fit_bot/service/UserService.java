package org.tytysh.fit_bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tytysh.fit_bot.config.ChatSession;
import org.tytysh.fit_bot.config.ChatSessionContextKeys;
import org.tytysh.fit_bot.dao.UserRepository;
import org.tytysh.fit_bot.dto.DtoToEntityMapper;
import org.tytysh.fit_bot.dto.UserDTO;
import org.tytysh.fit_bot.entity.User;
import org.tytysh.fit_bot.entity.Sex;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.tytysh.fit_bot.BotConstance.*;
import static org.tytysh.fit_bot.dto.DtoToEntityMapper.toDto;
import static org.tytysh.fit_bot.dto.DtoToEntityMapper.toEntity;
import static org.tytysh.fit_bot.i18n.ResponseMessages.*;

@Service
public class UserService implements ChatSessionContextKeys {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processRegistration(ChatSession chatSession) {
        if (chatSession.getUserDTO() == null) {
            UserDTO user = findUserByChatId(chatSession.getChatId());
            if (user != null) {
                chatSession.setUserDTO(user);
            } else {
                chatSession.setUserDTO(createUser(chatSession.getChatId()));
            }
        }
        UserDTO user = chatSession.getUserDTO();
        String message = chatSession.getLastMessage();
        if (!checkUserName(chatSession, user, message)) return;
        if (!checkUserEmail(chatSession, user, message)) return;
        if (!checkUserDateOfBirthWeightSex(chatSession, user, message)) return;
        if (!checkUserWeight(chatSession, user, message)) return;
        if (!checkUserSex(chatSession, user, message)) return;

         chatSession.addResponseMessage(USER_REGISTRATION_COMPLETE.getLocalizedMessage());
    }

    private boolean checkUserSex(ChatSession chatSession, UserDTO user, String message) {
        if (user.getSex() == null) {
            boolean isRequireSex = Boolean.TRUE.equals(chatSession.getSessionContext(REQUIRE_USER_SEX));
            if (isRequireSex){
                Sex sex = parseSex(message);
                if (sex != null) {
                    user.setSex(sex);
                    updateUser(user);
                    chatSession.removeSessionContext(REQUIRE_USER_SEX);
                    return true;
            }
            new IllegalArgumentException("Invalid sex");
                return false;

            } else {
                chatSession.addResponseMessage(ENTER_SEX.getLocalizedMessage());
                chatSession.setMessageProcessed();
                chatSession.setSessionContext(REQUIRE_USER_SEX, true);
                return false;
            }
        }
        return true;
    }

    private boolean checkUserWeight(ChatSession chatSession, UserDTO user, String message) {
        if (user.getWeight() == 0) {
            boolean isRequireWeight = Boolean.TRUE.equals(chatSession.getSessionContext(REQUIRE_USER_WEIGHT));
            if (isRequireWeight) {
                int weight = parseWeight(message);
                if (weight > 0 && weight < 300) {
                    user.setWeight(weight);
                    updateUser(user);
                    chatSession.removeSessionContext(REQUIRE_USER_WEIGHT);
                    return true;
                }
                new IllegalArgumentException("Invalid weight");
                return false;
            } else {
                chatSession.addResponseMessage(ENTER_WEIGHT.getLocalizedMessage());
                chatSession.setMessageProcessed();
                chatSession.setSessionContext(REQUIRE_USER_WEIGHT, true);
                return false;
            }
        }
        return true;
    }

    private boolean checkUserDateOfBirthWeightSex(ChatSession chatSession, UserDTO user, String message) {
        if (user.getDateOfBirth() == null) {
            boolean isRequireDateOfBirth = Boolean.TRUE.equals(chatSession.getSessionContext(REQUIRE_USER_DATE_OF_BERTH));
            if (isRequireDateOfBirth) {
                Date date = parseDate(message);
                if (date != null) {
                    user.setDateOfBirth(date);
                    updateUser(user);
                    chatSession.removeSessionContext(REQUIRE_USER_DATE_OF_BERTH);
                    return true;
                }
                new IllegalArgumentException("Invalid date of birth");
                return false;

            } else {
                chatSession.addResponseMessage(ENTER_DATE_OF_BERTH.getLocalizedMessage());
                chatSession.setMessageProcessed();
                chatSession.setSessionContext(REQUIRE_USER_DATE_OF_BERTH, true);
                return false;
            }
        }
        return true;

    }

    private boolean checkUserEmail(ChatSession chatSession, UserDTO user, String message) {
        if (user.getEmail() == null) {
            boolean isRequireEmail = Boolean.TRUE.equals(chatSession.getSessionContext(REQUIRE_USER_EMAIL));
            if (isRequireEmail) {
                if (PATTERN_EMAIL.matcher(message).matches()) {
                    user.setEmail(message);
                    updateUser(user);
                    chatSession.removeSessionContext(REQUIRE_USER_EMAIL);
                    return true;
                }
                new IllegalArgumentException("Invalid email address");
                return false;

                } else {
                    chatSession.addResponseMessage(ENTER_EMAIL.getLocalizedMessage());
                    chatSession.setMessageProcessed();
                    chatSession.setSessionContext(REQUIRE_USER_EMAIL, true);
                    return false;
                }
            }
            return true;
        }

    private boolean checkUserName(ChatSession chatSession, UserDTO user, String message) {
        if (user.getName() == null) {
            boolean isRequireName = Boolean.TRUE.equals(chatSession.getSessionContext(REQUIRE_USER_NAME));
            if (isRequireName) {
                user.setName(message);
                updateUser(user);
                chatSession.removeSessionContext(REQUIRE_USER_NAME);
                return true;
            } else {
                chatSession.addResponseMessage(ENTER_NAME.getLocalizedMessage());
                chatSession.setMessageProcessed();
                chatSession.setSessionContext(REQUIRE_USER_NAME, true);
                return false;
            }
        }
        return true;
    }

    private Sex parseSex(String message) {
        try {
            return Sex.valueOf(message);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Date parseDate(String message) {
        try {
            if (PATTERN_DATE_OF_BIRTH.matcher(message).matches()) {
                return Date.valueOf(message);
            }
            return null;
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("Error while parsing date", iae);
            iae.printStackTrace();
            return null;
        }
    }

    private int parseWeight(String message) {
        try {
            return Integer.parseInt(message.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public UserDTO findUserById(long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isEmpty()) return null;
        return toDto(result.get());
    }

    public UserDTO findUserByChatId(String chatId) {
        Optional<User> result = userRepository.findByChatId(chatId);
        if (result.isEmpty()) return null;
        return toDto(result.get());
    }

    public UserDTO createUser(String chatId) {
        User user = new User();
        user.setChatId(chatId);
        User entity = userRepository.save(user);
        return toDto(entity);
    }

    public UserDTO updateUser(UserDTO user) {
        User entity = toEntity(user);
        userRepository.save(entity);
        return toDto(entity);
    }

    public void deleteUser(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id must be between 1 and 9,223,372,036,854,775,807 digital input.");
        }
        User user = new User();
        user.setUserId(id);
        userRepository.delete(user);
    }


    public List<UserDTO> findAllUser() {
        List<User> entityList = userRepository.findAll();
        return entityList.stream().map(DtoToEntityMapper::toDto).toList();
    }



}
