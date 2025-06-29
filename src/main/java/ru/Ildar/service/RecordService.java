package ru.Ildar.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Ildar.dao.RecordDao;
import ru.Ildar.entity.Record;
import ru.Ildar.entity.RecordStatus;
import ru.Ildar.entity.dto.RecordsContainerDto;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordsContainerDto findAllRecords(String filterMode) {

       List<Record> records = recordDao.findAllRecords();
        int numberOfDoneRecords = (int)records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int)records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();

       if(filterMode ==null || filterMode.isBlank()){
            return new RecordsContainerDto(records, numberOfDoneRecords,numberOfActiveRecords);
       }

       String filterModeUpperCase  = filterMode.toUpperCase();
        List<String> allowedFilterModels = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
       if(allowedFilterModels.contains(filterModeUpperCase )){
            List<Record> filterRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDto(filterRecords, numberOfDoneRecords, numberOfActiveRecords );
       }else {
           return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
       }
    }

    public void saveRecord(String title) {
        System.out.println("Service: Получен title для сохранения: " + title);
        if(title != null && !title.isBlank()) {
            recordDao.saveRecord(new Record(title));
        }

        System.out.println("Service: Запись сохранена (предположительно).");
    }

     public void updateRecordStatus(int id, RecordStatus newStatus) {
        recordDao.updateRecordStatus(id, newStatus);
     }

     public  void  deleteRecord(int id) {
        recordDao.deleteRecord(id);
     }
}
