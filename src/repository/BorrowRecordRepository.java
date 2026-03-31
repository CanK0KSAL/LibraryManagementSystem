package repository;

import model.BorrowRecord;

import java.util.ArrayList;

public class BorrowRecordRepository {
    private ArrayList<BorrowRecord> records = new ArrayList<>();

    public void addRecord(BorrowRecord record) {
        records.add(record);
    }

    public ArrayList<BorrowRecord> getAllRecords() {
        return records;
    }

    public BorrowRecord findActiveByBookId(String bookId) {
        for (BorrowRecord record : records) {
            if (record.getBookId().equalsIgnoreCase(bookId) && !record.isReturned()) {
                return record;
            }
        }
        return null;
    }

    public BorrowRecord findActiveByBookIdAndUserId(String bookId, String userId) {
        for (BorrowRecord record : records) {
            if (record.getBookId().equalsIgnoreCase(bookId)
                    && record.getUserId().equalsIgnoreCase(userId)
                    && !record.isReturned()) {
                return record;
            }
        }
        return null;
    }

    public ArrayList<BorrowRecord> findByUserId(String userId) {
        ArrayList<BorrowRecord> results = new ArrayList<>();

        for (BorrowRecord record : records) {
            if (record.getUserId().equalsIgnoreCase(userId)) {
                results.add(record);
            }
        }

        return results;
    }

    public ArrayList<BorrowRecord> findActiveByUserId(String userId) {
        ArrayList<BorrowRecord> results = new ArrayList<>();

        for (BorrowRecord record : records) {
            if (record.getUserId().equalsIgnoreCase(userId) && !record.isReturned()) {
                results.add(record);
            }
        }

        return results;
    }
}