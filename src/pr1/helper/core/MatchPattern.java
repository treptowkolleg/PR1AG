/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pr1.helper.core;

import java.util.regex.Pattern;

public enum MatchPattern {
	INTEGER	("-?\\d+"),
	DOUBLE	("[-+]?(\\d*\\.\\d+|\\d+\\.\\d*)([eE][-+]?\\d+)?"),
	NUMBER	("[-+]?\\d*\\.?\\d+");

	private final String regex;
	private final Pattern pattern;
	MatchPattern(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getRegex() {
		return regex;
	}
}
