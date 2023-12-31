package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.MessageInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Named("simpleMessage")
    SimpleMessageDto messageToSimpleMessageDto(Message message);

    /**
     * This is necessary since the SimpleMessageDto misses the text property and the collection mapper can't handle
     * missing fields.
     **/
    @IterableMapping(qualifiedByName = "simpleMessage")
    List<SimpleMessageDto> messageToSimpleMessageDto(List<Message> message);

    DetailedMessageDto messageToDetailedMessageDto(Message message);

    Message detailedMessageDtoToMessage(DetailedMessageDto detailedMessageDto);

    Message messageInquiryDtoToMessage(MessageInquiryDto messageInquiryDto);

    MessageInquiryDto messageToMessageInquiryDto(Message message);
}

