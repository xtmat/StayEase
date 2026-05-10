package com.takehome.stayease.security.repository;

import com.takehome.stayease.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join t.user u where u.id = :id and (t.expired = false or t.revoked = false)")
    //                                        ^^^^^^^
    //                              join on the RELATIONSHIP, not a table
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
