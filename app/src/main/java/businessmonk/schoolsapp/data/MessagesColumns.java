package businessmonk.schoolsapp.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by ahmed on 19/08/16.
 */
public interface MessagesColumns {

	@DataType(INTEGER) @PrimaryKey
	@AutoIncrement
	String _ID = "_id";

	@DataType(TEXT) @NotNull
	String STUDENT_ID = "student_id";

	@DataType(TEXT) @NotNull
	String SUBJECT = "subject";

	@DataType(TEXT) @NotNull
	String CONTENT = "content";

	@DataType(TEXT) @NotNull
	String DATE = "date";
}