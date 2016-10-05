package businessmonk.schoolsapp.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by ahmed on 19/08/16.
 */

@ContentProvider(authority = MoviesProvider.AUTHORITY,database = MessagesDataBase.class)
public class MoviesProvider {
		public static final String AUTHORITY = "businessmonk.schoolsapp.data.MessagesProvider";

		static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

		interface Path {
			String Messages = "movies";
		}

		private static Uri buildUri(String... paths) {
			Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
			for (String path : paths) {
				builder.appendPath(path);
			}
			return builder.build();
		}

		@TableEndpoint(table = MessagesDataBase.Movies) public static class Movies {

			@ContentUri(
					path = Path.Messages,
					type = "vnd.android.cursor.dir/messages",
					defaultSort = MessagesColumns._ID + " ASC")
			public static final Uri CONTENT_URI = buildUri(Path.Messages);

			@InexactContentUri(
					path = Path.Messages + "/#",
					name = "MESSAGE_ID",
					type = "vnd.android.cursor.item/messages",
					whereColumn = MessagesColumns.MOVIE_ID,
					pathSegment = 1)
			public static Uri withId(long id) {
				return buildUri(Path.Messages, String.valueOf(id));
			}

		}
}
