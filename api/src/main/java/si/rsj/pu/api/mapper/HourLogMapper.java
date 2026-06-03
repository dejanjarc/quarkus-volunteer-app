package si.rsj.pu.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import si.rsj.pu.api.dto.request.CreateHourLogRequest;
import si.rsj.pu.api.dto.request.UpdateHourLogRequest;
import si.rsj.pu.api.dto.response.HourLogResponse;
import si.rsj.pu.entity.HourLog;
import si.rsj.pu.service.command.CreateHourLogCommand;
import si.rsj.pu.service.command.UpdateHourLogCommand;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HourLogMapper {

    CreateHourLogCommand toCreateCommand(CreateHourLogRequest request);

    UpdateHourLogCommand toUpdateCommand(UpdateHourLogRequest request);

    @Mapping(target = "volunteerId", source = "volunteer.id")
    @Mapping(target = "eventId", source = "event.id")
    HourLogResponse toResponse(HourLog hourLog);
}