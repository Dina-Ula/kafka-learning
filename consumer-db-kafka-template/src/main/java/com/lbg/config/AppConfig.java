package com.lbg.config;

import com.lbg.dao.FPSPaymentRepository;
import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import com.lbg.service.FPSPaymentConsumer;
import com.lbg.util.LBGInteractiveQueryService;
import com.lbg.util.ReferenceDataKTables;
import com.lbg.util.ReferenceDataValidator;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiConsumer;

@Configuration
public class AppConfig {

    @Autowired
    private FPSPaymentRepository fpsPaymentRepository;

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Bean
    public LBGInteractiveQueryService getLBGInteractiveQueryService() {
        return new LBGInteractiveQueryService(interactiveQueryService);
    }

    @Bean
    public ReferenceDataValidator referenceDataValidator(InteractiveQueryService interactiveQueryService) {
        return new ReferenceDataValidator(new ReferenceDataKTables(getLBGInteractiveQueryService()));
    }

    @Bean
    public BiConsumer<KStream<byte[], Event<FPSPayment>>, GlobalKTable<String, Event<ReferenceFPSSortCode>>> fpsPayment(final FPSPaymentRepository fpsPaymentRepository, final ReferenceDataValidator referenceDataValidator) {
        return new FPSPaymentConsumer(fpsPaymentRepository, referenceDataValidator);
    }
}
