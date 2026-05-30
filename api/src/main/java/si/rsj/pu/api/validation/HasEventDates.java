package si.rsj.pu.api.validation;

import java.time.OffsetDateTime;

public interface HasEventDates {
    OffsetDateTime startDate();
    OffsetDateTime endDate();
}