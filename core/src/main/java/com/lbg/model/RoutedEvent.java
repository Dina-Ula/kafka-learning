package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutedEvent {

    Event<ACSEvent> acsEvent;
    Event<MQEvent> mqEvent;
    String topicName;
    String route;
}
