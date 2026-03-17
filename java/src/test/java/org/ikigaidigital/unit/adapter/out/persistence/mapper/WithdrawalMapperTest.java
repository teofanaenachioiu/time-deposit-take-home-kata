package org.ikigaidigital.unit.adapter.out.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.ikigaidigital.adapter.out.persistence.entity.WithdrawalEntity;
import org.ikigaidigital.adapter.out.persistence.mapper.WithdrawalMapper;
import org.ikigaidigital.application.query.model.WithdrawalView;
import org.junit.jupiter.api.Test;

class WithdrawalMapperTest {

	@Test
	void toView_shouldMapAllFields() {
		WithdrawalEntity entity = WithdrawalEntity.builder().id(5L).amount(99.9).date(LocalDate.of(2026, 3, 1)).build();

		WithdrawalView view = WithdrawalMapper.toView(entity);

		assertThat(view.id()).isEqualTo(5L);
		assertThat(view.amount()).isEqualTo(99.9);
		assertThat(view.date()).isEqualTo(LocalDate.of(2026, 3, 1));
	}

	@Test
	void toViewList_shouldMapEachElement() {
		List<WithdrawalEntity> entities = List.of(
				WithdrawalEntity.builder().id(1L).amount(10.0).date(LocalDate.of(2026, 1, 1)).build(),
				WithdrawalEntity.builder().id(2L).amount(20.0).date(LocalDate.of(2026, 1, 2)).build());

		List<WithdrawalView> views = WithdrawalMapper.toView(entities);

		assertThat(views).hasSize(2);
		assertThat(views.get(0).id()).isEqualTo(1L);
		assertThat(views.get(1).id()).isEqualTo(2L);
	}
}
