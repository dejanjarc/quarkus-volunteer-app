package si.rsj.pu.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import si.rsj.pu.api.dto.request.CreateVolunteerRequest;
import si.rsj.pu.api.dto.request.UpdateVolunteerRequest;
import si.rsj.pu.api.dto.response.VolunteerResponse;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.service.command.CreateVolunteerCommand;
import si.rsj.pu.service.command.UpdateVolunteerCommand;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VolunteerMapper {

    CreateVolunteerCommand toCreateCommand(CreateVolunteerRequest request);

    UpdateVolunteerCommand toUpdateCommand(UpdateVolunteerRequest request);

    VolunteerResponse toResponse(Volunteer volunteer);
}