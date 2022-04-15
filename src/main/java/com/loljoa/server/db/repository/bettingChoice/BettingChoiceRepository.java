package com.loljoa.server.db.repository.bettingChoice;

import com.loljoa.server.db.entity.BettingChoice;
import com.loljoa.server.db.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BettingChoiceRepository extends CommonRepository<BettingChoice, Long>, BettingChoiceRepositoryCustom {
}
