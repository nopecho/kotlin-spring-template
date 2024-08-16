package com.nopecho.common.web

import com.nopecho.common.web.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionController {

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(404, e.message).run {
            logging()
            status(HttpStatus.NOT_FOUND).body(this)
        }
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(400, e.message).run {
            logging()
            badRequest().body(this)
        }
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(400, e.message).run {
            logging()
            badRequest().body(this)
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val messages = e.bindingResult.allErrors.joinToString(" | ") { it.defaultMessage ?: "" }
        return ErrorResponse(400, messages).run {
            logging()
            badRequest().body(this)
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(400, "필수 파라미터가 존재하지 않습니다. [${e.parameterName}]").run {
            logging(e.message)
            badRequest().body(this)
        }
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFoundException(e: NoResourceFoundException): ResponseEntity<ErrorResponse> {
        return ErrorResponse(404, e.toMessage()).run {
            logging()
            status(HttpStatus.NOT_FOUND).body(this)
        }
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ResponseEntity<ErrorResponse> {
        return ErrorResponse(500, "알 수 없는 서버 문제가 발생했습니다.").run {
            logging("${e.javaClass.simpleName} : ${e.message}")
            internalServerError().body(this)
        }
    }

    private fun NoResourceFoundException.toMessage(): String {
        return "요청 경로를 찾을 수 없습니다. ${httpMethod.name()} $resourcePath"
    }
}