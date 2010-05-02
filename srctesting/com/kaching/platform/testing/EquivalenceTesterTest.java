/**
 * Copyright 2009 KaChing Group Inc. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.kaching.platform.testing;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import org.junit.Test;

public class EquivalenceTesterTest {

  @Test(expected = AssertionError.class)
  public void nothinCanBeEqualToNull() {
    EquivalenceTester.check(newArrayList(new EqualsDoesNotHandleNullArg()));
  }
  
  
  static class EqualsDoesNotHandleNullArg {
    @Override
    public boolean equals(Object that) {
      checkNotNull(that);
      return true;
    }
  }
  
}
