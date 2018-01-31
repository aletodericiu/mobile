package ro.ni.bbs.dtoconverter;

import java.util.List;

/**
 * Created by Rares Abrudan 15.08.2017.
 */
public interface Converter<TElem, DTOTElem>
{
    DTOTElem toDTO(TElem element);

    List<DTOTElem> toDTOs(List<TElem> elements);

    TElem toEntity(DTOTElem dtoElement);

    List<TElem> toEntitys(List<DTOTElem> dtoElements);
}