package org.ikigaidigital.adapter.persistence.mapper;

import org.ikigaidigital.adapter.persistence.entity.TimeDepositEntity;
import org.ikigaidigital.domain.model.PlanType;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDepositMapperTest {

    @Test
    void toDomain_shouldMapAllFields() {
        TimeDepositEntity entity = TimeDepositEntity.builder()
                .id(1)
                .planType(PlanType.BASIC)
                .balance(100.0)
                .days(60)
                .build();

        TimeDeposit domain = TimeDepositMapper.toDomain(entity);

        assertThat(domain.getId()).isEqualTo(1);
        assertThat(domain.getPlanType()).isEqualTo("BASIC");
        assertThat(domain.getBalance()).isEqualTo(100.0);
        assertThat(domain.getDays()).isEqualTo(60);
    }

    @Test
    void toEntity_shouldMapAllFields() {
        TimeDeposit domain = new TimeDeposit(2, "premium", 250.0, 90);

        TimeDepositEntity entity = TimeDepositMapper.toEntity(domain);

        assertThat(entity.getId()).isEqualTo(2);
        assertThat(entity.getPlanType()).isEqualTo(PlanType.PREMIUM);
        assertThat(entity.getBalance()).isEqualTo(250.0);
        assertThat(entity.getDays()).isEqualTo(90);
    }

    @Test
    void toDomainList_shouldMapEachElement() {
        List<TimeDepositEntity> entities = List.of(
                TimeDepositEntity.builder().id(1).planType(PlanType.BASIC).balance(10.0).days(10).build(),
                TimeDepositEntity.builder().id(2).planType(PlanType.STUDENT).balance(20.0).days(20).build()
        );

        List<TimeDeposit> domains = TimeDepositMapper.toDomain(entities);

        assertThat(domains).hasSize(2);
        assertThat(domains.get(0).getId()).isEqualTo(1);
        assertThat(domains.get(1).getId()).isEqualTo(2);
    }

    @Test
    void toEntityList_shouldMapEachElement() {
        List<TimeDeposit> domains = List.of(
                new TimeDeposit(1, "basic", 10.0, 10),
                new TimeDeposit(2, "student", 20.0, 20)
        );

        List<TimeDepositEntity> entities = TimeDepositMapper.toEntity(domains);

        assertThat(entities).hasSize(2);
        assertThat(entities.get(0).getPlanType()).isEqualTo(PlanType.BASIC);
        assertThat(entities.get(1).getPlanType()).isEqualTo(PlanType.STUDENT);
    }
}
