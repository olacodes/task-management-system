package com.olacodes.taskservice.service

import com.amazonaws.services.sqs.AmazonSQSAsync
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.aws.messaging.core.QueueMessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class TaskMessageService @Autowired constructor(private val amazonSQSAsync: AmazonSQSAsync) {
    companion object {
        private const val QUEUE_NAME = "https://sqs.us-east-1.amazonaws.com/XXXX/TaskManagementServiceSQS"
    }

    fun send(messagePayload: String): Boolean {
        val messageChannel = QueueMessageChannel(amazonSQSAsync, QUEUE_NAME)
        val msg = MessageBuilder.withPayload(messagePayload).setHeader("sender", "task-service").build()
        val waitTimeoutMillis = 5000L
        return messageChannel.send(msg, waitTimeoutMillis)
    }
}
