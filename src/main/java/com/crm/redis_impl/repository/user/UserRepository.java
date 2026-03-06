package com.crm.redis_impl.repository.user;

import com.crm.redis_impl.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM users WHERE (username = :identifier OR email = :identifier) AND is_deleted = 0", nativeQuery = true)
    Optional<UserEntity> findActiveByUsernameOrEmail(@Param("identifier") String identifier);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT u.* FROM users u " +
            "INNER JOIN user_roles ur ON u.id = ur.user_id " +
            "INNER JOIN roles r ON r.id = ur.role_id " +
            "WHERE r.name = :roleName AND u.is_deleted = :isDeleted ORDER BY u.id", nativeQuery = true)
    List<UserEntity> findActiveUsersByRoleNative(@Param("roleName") String roleName, @Param("isDeleted") int isDeleted);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password = :newPassword, is_password_changed = 1 WHERE id = :userId", nativeQuery = true)
    void updatePasswordAndStatusNative(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_deleted = 1 WHERE id = :userId", nativeQuery = true)
    void deactivateUserNative(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = """
            SELECT u.is_deleted from users u where u.id = :id;
            """)
    Integer activeById(Long id);
}