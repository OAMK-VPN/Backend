package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Response.ParceResponse;

@Component
public class ParcelMapper {
     @Autowired
    private ModelMapper modelMapper;

    public ParceResponse mapParcelToResponse(Parcel parcel) {
        return modelMapper.map(parcel, ParceResponse.class);
    }

}
