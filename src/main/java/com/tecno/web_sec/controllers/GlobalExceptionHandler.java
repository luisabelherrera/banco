package com.tecno.web_sec.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Clase para manejar excepciones globales en la aplicación.
 * Proporciona métodos para capturar y gestionar excepciones de manera
 * centralizada.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo IllegalStateException.
     *
     * @param ex la excepción capturada
     * @return un objeto ModelAndView con el mensaje de error y la vista a mostrar
     */
    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }

    /**
     * Maneja excepciones generales de tipo Exception.
     *
     * @param ex la excepción capturada
     * @return un objeto ModelAndView con el mensaje de error y la vista a mostrar
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
