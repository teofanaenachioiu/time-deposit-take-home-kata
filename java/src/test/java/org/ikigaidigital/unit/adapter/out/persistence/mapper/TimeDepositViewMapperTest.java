package org.ikigaidigital.unit.adapter.out.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.ikigaidigital.adapter.out.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.out.persistence.entity.WithdrawalEntity;
import org.ikigaidigital.adapter.out.persistence.mapper.TimeDepositViewMapper;
import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.domain.model.PlanType;
import org.junit.jupiter.api.Test;

class TimeDepositViewMapperTest {

	@Test
	void toView_shouldMapAllFieldsIncludingWithdrawals() {
		TimeDepositEntity entity = TimeDepositEntity.builder().id(1).planType(PlanType.STUDENT)
				.balance(new BigDecimal("300.00")).days(120)
				.withdrawals(List.of(WithdrawalEntity.builder().id(9).amount(new BigDecimal("15.00"))
						.date(LocalDate.of(2026, 2, 14)).build()))
				.build();

		TimeDepositView view = TimeDepositViewMapper.toView(entity);

		assertThat(view.id()).isEqualTo(1);
		assertThat(view.planType()).isEqualTo(PlanType.STUDENT);
		assertThat(view.balance()).isEqualByComparingTo("300.00");
		assertThat(view.days()).isEqualTo(120);
		assertThat(view.withdrawals()).hasSize(1);
		assertThat(view.withdrawals().get(0).id()).isEqualTo(9);
	}

	@Test
	void toViewList_shouldMapEachElement() {
		List<TimeDepositEntity> entities = List.of(
				TimeDepositEntity.builder().id(1).planType(PlanType.BASIC).balance(new BigDecimal("10.00")).days(10)
						.withdrawals(List.of()).build(),
				TimeDepositEntity.builder().id(2).planType(PlanType.PREMIUM).balance(new BigDecimal("20.00"))
						.days(20).withdrawals(List.of()).build());

		List<TimeDepositView> views = TimeDepositViewMapper.toView(entities);

		assertThat(views).hasSize(2);
		assertThat(views.get(0).id()).isEqualTo(1);
		assertThat(views.get(1).id()).isEqualTo(2);
	}
}
