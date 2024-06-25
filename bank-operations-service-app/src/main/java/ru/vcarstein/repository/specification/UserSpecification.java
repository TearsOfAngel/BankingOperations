package ru.vcarstein.repository.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.vcarstein.entity.User;

import java.time.LocalDate;

@UtilityClass
public class UserSpecification {
    public Specification<User> fullNameLike(String fullName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("fullName"), fullName + "%");
    }

    public Specification<User> phoneEqual(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("phone"), phone);
    }

    public Specification<User> emailEqual(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public Specification<User> birthDateGreaterThan(LocalDate birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("birthDate"), birthDate);
    }
}
