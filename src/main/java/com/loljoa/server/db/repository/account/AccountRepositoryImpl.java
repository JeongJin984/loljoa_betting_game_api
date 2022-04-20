package com.loljoa.server.db.repository.account;

import com.loljoa.server.db.entity.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.loljoa.server.db.entity.QAccount.account;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {
    private final JPAQueryFactory factory;

    @Override
    public Account getAccountById(Long id) {
        return factory
                .selectFrom(account)
                .where(account.accountId.eq(id))
                .fetchOne();
    }

    @Override
    public Account getAccountByUsername(String username) {
        return factory
                .selectFrom(account)
                .where(account.username.eq(username))
                .fetchOne();
    }
}
