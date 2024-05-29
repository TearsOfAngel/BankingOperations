package ru.vcarstein.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.vcarstein.entity.User;

import java.time.LocalDate;

public class UserSpecification {
    public static Specification<User> fullNameLike(String fullName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("fullName"), fullName + "%");
    }

    public static Specification<User> phoneEqual(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("phone"), phone);
    }

    public static Specification<User> emailEqual(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<User> birthDateGreaterThan(LocalDate birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("birthDate"), birthDate);
    }
}
