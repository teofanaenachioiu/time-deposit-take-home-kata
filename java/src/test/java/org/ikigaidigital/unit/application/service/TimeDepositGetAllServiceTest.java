package org.ikigaidigital.unit.application.service;

import org.ikigaidigital.application.query.model.TimeDepositView;
import org.ikigaidigital.application.service.TimeDepositGetAllService;
import org.ikigaidigital.port.out.TimeDepositQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeDepositGetAllServiceTest {

    @Mock
    private TimeDepositQueryRepository queryRepository;

    @Test
    void getAll_shouldReturnRepositoryResult() {
        TimeDepositGetAllService service = new TimeDepositGetAllService(queryRepository);
        List<TimeDepositView> expected = List.of(
                TimeDepositView.builder().id(1).build(),
                TimeDepositView.builder().id(2).build()
        );
        when(queryRepository.findAllWithWithdrawals()).thenReturn(expected);

        List<TimeDepositView> result = service.getAll();

        assertThat(result).isEqualTo(expected);
        verify(queryRepository).findAllWithWithdrawals();
        verifyNoMoreInteractions(queryRepository);
    }

}
