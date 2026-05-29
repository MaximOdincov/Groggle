package com.example.todolist.data.mapper;

import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.model.CurrencyRate;

import java.util.List;
import java.util.stream.Collectors;

public class NoteMapper {
    public static Note toDomain(com.example.todolist.data.local.NoteEntity entity) {
        return new Note(
                entity.getId(),
                entity.getText(),
                entity.getPriceRub(),
                entity.isBought(),
                entity.getPriority()
        );
    }

    public static com.example.todolist.data.local.NoteEntity toEntity(Note note) {
        return new com.example.todolist.data.local.NoteEntity(
                note.getId(),
                note.getText(),
                note.getPriceRub(),
                note.isBought(),
                note.getPriority()
        );
    }

    public static CurrencyRate toDomain(com.example.todolist.data.local.CurrencyRateEntity entity) {
        return new CurrencyRate(
                entity.getCode(),
                entity.getRate(),
                entity.getUpdatedAt()
        );
    }

    public static com.example.todolist.data.local.CurrencyRateEntity toEntity(CurrencyRate rate) {
        return new com.example.todolist.data.local.CurrencyRateEntity(
                rate.getCode(),
                rate.getRate(),
                rate.getUpdatedAt()
        );
    }

    public static List<Note> toDomainList(List<com.example.todolist.data.local.NoteEntity> entities) {
        return entities.stream().map(NoteMapper::toDomain).collect(Collectors.toList());
    }

    public static List<CurrencyRate> toDomainList(List<com.example.todolist.data.local.CurrencyRateEntity> entities) {
        return entities.stream().map(NoteMapper::toDomain).collect(Collectors.toList());
    }
}
