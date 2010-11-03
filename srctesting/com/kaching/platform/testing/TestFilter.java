/**
 * Copyright 2009 Wealthfront Inc. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.kaching.platform.testing;

/**
 * Filters tests to determine whether they should be included in
 * suites built using the {@link TestSuiteBuilder}.
 */
public interface TestFilter {

  /**
   * Returns {@code true} if the test should be included, {@code false}
   * otherwise.
   */
  boolean shouldIncludeTest(Class<?> test);
  
}
