/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.car.app.model;

import static com.google.common.truth.Truth.assertThat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link DurationSpan}. */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class DurationSpanTest {
    @Test
    public void constructor() {
        DurationSpan span = DurationSpan.create(1);
        assertThat(span.getDurationSeconds()).isEqualTo(1);
    }

    @Test
    public void equals() {
        DurationSpan span = DurationSpan.create(1);
        assertThat(span).isEqualTo(DurationSpan.create(1));
    }

    @Test
    public void notEquals() {
        DurationSpan span = DurationSpan.create(1);
        assertThat(span).isNotEqualTo(DurationSpan.create(2));
    }
}
