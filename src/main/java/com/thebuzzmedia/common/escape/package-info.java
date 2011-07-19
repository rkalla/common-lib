/**   
 * Copyright 2011 The Buzz Media, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copied directly from Google's GDATA client to get an implementation of 
 * correct OAuth-compliant encoding.
 * <p/>
 * These classes were copied directly so as to avoid pulling in the 240kb
 * dependency JAR, blowing this library up to 20x it's current size. 
 */
package com.thebuzzmedia.common.escape;