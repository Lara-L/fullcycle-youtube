package main

import (
	"fmt"
	"github.com/streadway/amqp"
	"log"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func main() {
	conn, err := amqp.Dial("amqp://admin:123456@localhost:5672/")
	failOnError(err, "Falha ao conectar ao RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Falha ao abrir o canal")
	defer ch.Close()

	q, err := ch.QueueDeclare(
		"upload.complete",
		true,
		false,
		false,
		false,
		nil,
	)
	failOnError(err, "Falha ao declarar a fila")

	msgs, err := ch.Consume(
		q.Name,
		"",
		true,
		false,
		false,
		false,
		nil,
	)
	failOnError(err, "Falha ao registrar o consumidor")

	forever := make(chan bool)

	go func() {
		for d := range msgs {
			fileId := string(d.Body)
			fmt.Printf("Recebido evento de upload completo para o arquivo: %s\n", fileId)

			processVideo(fileId)
		}
	}()

	log.Printf(" [*] Aguardando mensagens. Para sair pressione CTRL+C")
	<-forever
}

func processVideo(fileId string) {
	fmt.Printf("Processando video com ID: %s\n", fileId)
}
