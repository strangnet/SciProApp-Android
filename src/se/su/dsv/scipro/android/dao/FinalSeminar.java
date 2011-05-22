/*
 * Copyright (c) 2011 Patrick Strang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.su.dsv.scipro.android.dao;

import java.util.List;

/**
 * User: patrick
 * Date: 2011-05-22
 * Time: 15:36
 */
public class FinalSeminar {
    public String room;
    public String date;
    public List<User> activeListeners;
    public List<User> opponents;

    public FinalSeminar(String room, String date, List<User> activeListeners, List<User> opponents) {
        this.room = room;
        this.date = date;
        this.activeListeners = activeListeners;
        this.opponents = opponents;
    }
}
