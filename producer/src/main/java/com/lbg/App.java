package com.lbg;

import com.lbg.model.*;
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

    private static final boolean GENERATE_SORT_CODES = false;

    private static final boolean GENERATE_PAYMENTS = false;

    private static final boolean GENERATE_PAYMENTS_INBOUND = false;

    private static final boolean GENERATE_PAYMENTS_OUTBOUND = false;

    LinkedList<Event<ReferenceFPSSortCode>> referenceFPSSortCodes = new LinkedList<>(generateReferenceFPSSortCodes());

    LinkedList<Event<FPSPayment>> fpsPayments = new LinkedList<>(generateFPSPayments());

    LinkedList<Event<FPSPayment>> fpsPaymentsInbound = new LinkedList<>(generateFPSPaymentsInbound());

    LinkedList<Event<FPSPayment>> fpsPaymentsOutbound = new LinkedList<>(generateFPSPaymentsOutbound());

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
            for (int i = 0; i < 200000; i++) {
                list.add(Event.of(new FPSPayment(UUID.randomUUID().toString(), getRandomAccountNo(), getRandomSortCode(), getRandomAmount(), "31-12-2023", "1")));
            }
        }

        return list;
    }

    public static List<Event<FPSPayment>> generateFPSPaymentsInbound() {

        List<Event<FPSPayment>> list = new ArrayList<>();

        if (GENERATE_PAYMENTS_INBOUND) {
            for (int i = 0; i < 1; i++) {
                list.add(Event.of(new FPSPayment(UUID.randomUUID().toString(), getRandomAccountNo(), getRandomSortCode(), getRandomAmount(), "23-11-2023", "1")));
            }
        }

        return list;
    }

    public static List<Event<FPSPayment>> generateFPSPaymentsOutbound() {

        List<Event<FPSPayment>> list = new ArrayList<>();

        if (GENERATE_PAYMENTS_OUTBOUND) {
            for (int i = 0; i < 200000; i++) {
                list.add(Event.of(new FPSPayment(UUID.randomUUID().toString(), getRandomAccountNo(), getRandomSortCode(), getRandomAmount(), "23-11-2023", "1")));
            }
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
        int high = 1000000;

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

    @Bean
    public Supplier<Message<Event<FPSPayment>>> fpsPaymentInboundSupplier() {
        return () -> {
            if (fpsPaymentsInbound.peek() != null) {
                Message<Event<FPSPayment>> o = MessageBuilder
                        .withPayload(fpsPaymentsInbound.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(fpsPaymentsInbound.poll()).getData().getId())
                        .build();
                System.out.println("FPS Payment Inbound: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<Message<Event<FPSPayment>>> fpsPaymentOutboundSupplier() {
        return () -> {
            if (fpsPaymentsOutbound.peek() != null) {
                Message<Event<FPSPayment>> o = MessageBuilder
                        .withPayload(fpsPaymentsOutbound.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(fpsPaymentsOutbound.poll()).getData().getId())
                        .build();
                System.out.println("FPS Payment Outbound: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    private static final boolean GENERATE_ACS_REQUEST_TRANSACTIONS = true;

    LinkedList<Event<ACSEvent>> acsRequestTransactions = new LinkedList<>(generateAcsRequestTransactions());

    public static List<Event<ACSEvent>> generateAcsRequestTransactions() {

        List<Event<ACSEvent>> list = new ArrayList<>();

        if (GENERATE_ACS_REQUEST_TRANSACTIONS) {
            for (int i = 1; i < 30000; i++) {
                list.add(new Event<>("acs_request_transactions", new ACSEvent(String.valueOf(i))));
            }
        }

        return list;
    }

    @Bean
    public Supplier<Message<Event<ACSEvent>>> acsRequestTransactions() {
        return () -> {
            if (acsRequestTransactions.peek() != null) {
                Message<Event<ACSEvent>> o = MessageBuilder
                        .withPayload(acsRequestTransactions.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(acsRequestTransactions.poll()).getData().getData())
                        .build();
                System.out.println("ACS Request Transactions: " + o.getPayload());
                return o;
            } else {
                return null;
            }
        };
    }

    private static final boolean GENERATE_MQ_RESPONSE_TRANSACTIONS = false;
    LinkedList<Event<MQEvent>> mqResponseTransactions = new LinkedList<>(generateMqResponseTransactions());

    public static List<Event<MQEvent>> generateMqResponseTransactions() {

        List<Event<MQEvent>> list = new ArrayList<>();

        if (GENERATE_MQ_RESPONSE_TRANSACTIONS) {
            for (int i = 1; i < 30000; i++) {
                list.add(new Event<>("mq_request_transactions", new MQEvent("", String.valueOf(i))));
            }
        }

        return list;
    }

    @Bean
    public Supplier<Message<Event<MQEvent>>> mqResponseTransactions() {
        return () -> {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (mqResponseTransactions.peek() != null) {
                Message<Event<MQEvent>> o = MessageBuilder
                        .withPayload(mqResponseTransactions.peek())
                        .setHeader(KafkaHeaders.KEY, Objects.requireNonNull(mqResponseTransactions.poll()).getData().getTargetData() + "-mq")
                        .build();
                System.out.println("MQ Request Transactions: " + o.getPayload());
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
