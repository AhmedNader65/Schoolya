package businessmonk.schoolsapp.data;

import android.support.annotation.Nullable;

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

	@DataType(INTEGER) @Nullable
	String STUDENT_ID = "student_id";

	@DataType(TEXT) @NotNull
	String TYPE = "type";

	@DataType(TEXT) @NotNull
	String SUBJECT = "subject";

	@DataType(TEXT) @NotNull
	String CONTENT = "content";

	@DataType(TEXT) @NotNull
	String DATE = "date";


	@DataType(TEXT) @Nullable
	String IMAGE = "image";


	@DataType(INTEGER) @NotNull
	String INBOX = "inbox";
}