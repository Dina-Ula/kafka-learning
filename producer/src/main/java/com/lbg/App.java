package com.lbg;

import com.lbg.model.Event;
import com.lbg.model.FPSPayment;
import com.lbg.model.ReferenceFPSSortCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.*;
import java.util.function.Supplier;

@SpringBootApplication
public class App {

    private static final boolean GENERATE_SORT_CODES = true;

    private static final boolean GENERATE_PAYMENTS = false;

    LinkedList<Event<ReferenceFPSSortCode>> referenceFPSSortCodes = new LinkedList<>(generateReferenceFPSSortCodes());

    LinkedList<Event<FPSPayment>> fpsPayments = new LinkedList<>(generateFPSPayments());

    public static List<Event<ReferenceFPSSortCode>> generateReferenceFPSSortCodes() {

        List<Event<ReferenceFPSSortCode>> list = new ArrayList<>();

        if (GENERATE_SORT_CODES) {
            for (int i = 100000; i < 300000; i++) {
                list.add(Event.of(new ReferenceFPSSortCode(String.valueOf(i), Boolean.TRUE)));
            }
        }

        return list;
    }

    public static List<Event<FPSPayment>> generateFPSPayments() {

        List<Event<FPSPayment>> list = new ArrayList<>();

        if (GENERATE_PAYMENTS) {
            /*for (int i = 0; i < 200000; i++) {
                list.add(Event.of(new FPSPayment(UUID.randomUUID().toString(), getRandomAccountNo(), getRandomSortCode(), getRandomAmount())));
            }*/
            list.add(Event.of(new FPSPayment(UUID.randomUUID().toString(), getRandomAccountNo(), "107920", getRandomAmount())));
        }

        return list;
    }

    public static String getRandomAccountNo() {

        Random r = new Random();
        int low = 70000000;
        int high = 70000100;

        return String.valueOf(r.nextInt(high - low) + low);
    }

    public static String getRandomSortCode() {

        Random r = new Random();
        int low = 100000;
        int high = 305000;

        return String.valueOf(r.nextInt(high - low) + low);
    }

    public static String getRandomAmount() {
        return String.valueOf(new Random().nextInt(9999));
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier1() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier2() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier3() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier4() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier5() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier6() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier7() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<ReferenceFPSSortCode>>> referenceFPSSortCodesSupplier8() {
        return () -> {
            if (referenceFPSSortCodes.peek() != null) {
                Message<Event<ReferenceFPSSortCode>> o = MessageBuilder
                        .withPayload(referenceFPSSortCodes.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(referenceFPSSortCodes.poll()).getData().getSortCode())
                        .build();
                System.out.println("Reference FPS Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<FPSPayment>>> fpsPaymentSupplier() {
        return () -> {
            if (fpsPayments.peek() != null) {
                Message<Event<FPSPayment>> o = MessageBuilder
                        .withPayload(fpsPayments.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(fpsPayments.poll()).getData().getId())
                        .build();
                System.out.println("FPS Payment Sort Codes: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
