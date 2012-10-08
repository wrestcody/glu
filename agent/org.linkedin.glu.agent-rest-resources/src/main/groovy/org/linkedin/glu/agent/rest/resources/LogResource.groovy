/*
 * Copyright (c) 2010-2010 LinkedIn, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.linkedin.glu.agent.rest.resources

import org.restlet.Context
import org.restlet.Request
import org.restlet.Response
import org.restlet.representation.Representation
import org.restlet.representation.Variant
import org.linkedin.util.io.PathUtils
import org.linkedin.glu.agent.rest.common.InputStreamOutputRepresentation

/**
 * Handles resources to the agent logs
 *
 * @author ypujante@linkedin.com */
class LogResource extends BaseResource
{
  LogResource(Context context, Request request, Response response)
  {
    super(context, request, response);
  }

  public boolean allowGet()
  {
    return true
  }

  /**
   * GET: return the log
   */
  public Representation represent(Variant variant)
  {
    return noException {
      def args = toArgs(request.originalRef.queryAsForm)
      def log = PathUtils.removeLeadingSlash(path)
      if(log)
      {
        args.log = log
      }

      def res = agent.tailAgentLog(args)
      if(res instanceof InputStream)
      {
        return new InputStreamOutputRepresentation(res)
      }
      else
      {
        return toRepresentation([res: res])
      }
    }
  }
}
