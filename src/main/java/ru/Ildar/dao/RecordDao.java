package ru.Ildar.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.Ildar.entity.Record;
import ru.Ildar.entity.RecordStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
@Scope("singleton")
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record("Купить машину", RecordStatus.ACTIVE),
                    new Record("Помыть голову", RecordStatus.DONE),
                    new Record("Посмотреть билеты", RecordStatus.ACTIVE)
            )
    );


    public List<Record> findAllRecords() {
        return new ArrayList<>(records);
    }


    public void saveRecord(Record record) {
        System.out.println("DAO: Добавляем в список запись с title: " + record.getTitle());
        records.add(record);

        System.out.println("DAO: Текущее состояние списка записей:");
        for (Record r : records) {
            System.out.println("  - " + r.getTitle() + " (" + r.getStatus() + ")");
        }
    }

    public void updateRecordStatus (int id, RecordStatus newStatus) {
        for(Record r : records) {
            if(r.getId()==id) {
                r.setStatus(newStatus);
                break;
            }
        }

    }

    public void  deleteRecord(int id) {
       records.removeIf(r -> r.getId()==id);
    }
}