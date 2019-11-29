package se.complexjava.videostreamingapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import se.complexjava.videostreamingapi.entity.Comment;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "text body cant'be bull or empty")
  private String textBody;

  @NotEmpty(message = "date can't be null or empty")
  private Instant dateCreated;

  private static ModelMapper modelMapper = new ModelMapper();



  public static CommentModel fromEntity(Comment entity){
    return modelMapper.map(entity, CommentModel.class);
  }


  public static List<CommentModel> fromEntity(Iterable<Comment> entities){
    List<CommentModel> models = new ArrayList<>();
    for (Comment entity : entities) {
      models.add(fromEntity(entity));
    }
    return models;
  }
}
