package businessmonk.schoolsapp.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by ahmed on 19/08/16.
 */

@ContentProvider(authority = MessagesProvider.AUTHORITY,database = MessagesDataBase.class)
public class MessagesProvider {
		public static final String AUTHORITY = "businessmonk.schoolsapp.data.MessagesProvider";

		static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

		interface Path {
			String Messages = "messages";
		}

		private static Uri buildUri(String... paths) {
			Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
			for (String path : paths) {
				builder.appendPath(path);
			}
			return builder.build();
		}

		@TableEndpoint(table = MessagesDataBase.Messages) public static class Messages {

			@ContentUri(
					path = Path.Messages,
					type = "vnd.android.cursor.dir/messages",
					defaultSort = MessagesColumns._ID + " ASC")
			public static final Uri CONTENT_URI = buildUri(Path.Messages);
		}
}
