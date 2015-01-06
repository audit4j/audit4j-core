/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.audit4j.core.extra.cron4j;

/**
 * <p>
 * This interface describes a task collector. Task collectors can be registered
 * in a {@link Scheduler} instance with the
 * {@link Scheduler#addTaskCollector(TaskCollector)} method. Any registered task
 * collector is queried by the scheduler once a minute. The developer has to
 * implement the {@link TaskCollector#getTasks()} method, returning a
 * {@link TaskTable} whose elements has been collected with a custom logic. In
 * example the list can be extracted from a database.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
public interface TaskCollector {

	/**
	 * Once the instance has been registered on a {@link Scheduler} instance,
	 * with the {@link Scheduler#addTaskCollector(TaskCollector)} method, this
	 * method will be queried once a minute. It should return a custom
	 * {@link TaskTable} object. The scheduler instance will automatically
	 * iterate over the returned table elements, executing any task whose
	 * scheduling pattern is matching the current system time.
	 * 
	 * @return The task table that will be automatically injected in the
	 *         scheduler.
	 */
	public TaskTable getTasks();

}
