import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("trigger")
    input {
        triggeredBy("trigger()")
    }
    outputMessage {
        sentTo("topic1")
        headers {
            header("amqp_receivedRoutingKey", "#")
        }
        body([
                name: "Bathu",
                age: regex("[1-9][8-9]")
        ])
    }
}



