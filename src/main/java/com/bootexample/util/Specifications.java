package com.bootexample.util;

import com.bootexample.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.bootexample.util.BootUtils.isNOE;

/**
 * Created: antosha4e
 * Date: 27.05.16
 */
public final class Specifications {
    public static Specification<User> findAllUsers(String sort) {
        return new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                if(!isNOE(sort)) {
                    boolean asc = sort.startsWith("+");
                    String field = "name";

                    if(sort.contains("email")) {
                        field = "email";
                    }

                    if(asc) {
                        query = query.orderBy(builder.asc(root.get(field)));
                    } else {
                        query = query.orderBy(builder.desc(root.get(field)));
                    }
                }

                return query.getRestriction();
            }
        };
    }
}