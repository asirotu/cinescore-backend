package br.com.cinescore.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomModelMapper {

    public static ModelMapper mapper = new ModelMapper();

    public static<Origin, Destination>Destination parseObject(Origin origin, Class<Destination> destination){
        return mapper.map(origin, destination);
    }

    public static <Origin, Destination> List<Destination> parseObjectList(List<Origin> origin, Class<Destination> destination) {
        return origin.stream()
                .map(o -> mapper.map(o, destination))
                .collect(Collectors.toList());
    }

}
