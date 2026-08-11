package ru.vadim.orderservice.exception;

public sealed interface ApplicationError permits DomainError, SystemError {
}
