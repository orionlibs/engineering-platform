package io.github.orionlibs.core.database.store_and_forward;

import io.github.orionlibs.core.database.DAO;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StoreAndForwardEngine
{
    private final Queue<DatabaseUpdateEvent> eventQueue = new ConcurrentLinkedQueue<>();
    private final int sizeOfCachedDatabaseEvents;
    private final DAO dao;
    private volatile boolean running = true;
    private Thread forwardThread;


    public StoreAndForwardEngine(DAO dao, int sizeOfCachedDatabaseEvents)
    {
        this.dao = dao;
        this.sizeOfCachedDatabaseEvents = sizeOfCachedDatabaseEvents;
        startForwardingTask();
    }


    public void storeEvent(DatabaseUpdateEvent event)
    {
        if(eventQueue.size() < sizeOfCachedDatabaseEvents)
        {
            eventQueue.add(event);
        }
    }


    public void startForwardingTask()
    {
        forwardThread = Thread.startVirtualThread(() -> {
            while(running)
            {
                forwardEvents();
                try
                {
                    Thread.sleep(3_000);
                }
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }


    private void forwardEvents()
    {
        if(eventQueue.isEmpty())
        {
            return;
        }
        while(!eventQueue.isEmpty())
        {
            DatabaseUpdateEvent event = eventQueue.peek();
            if(event.getOperationType() == DatabaseUpdateEventType.SAVE || event.getOperationType() == DatabaseUpdateEventType.UPDATE)
            {
                try
                {
                    dao.save(event.getModel());
                    eventQueue.poll();
                }
                catch(Throwable e)
                {
                    System.err.println("Problem forwarding task: " + e.getMessage());
                }
            }
            /*else if(event.getOperationType() == DatabaseUpdateEventType.UPDATE)
            {
                try
                {
                    if(event.getColumnsToUpdate() != null)
                    {
                        dao.save(event.getModel(),
                                        event.getDatabaseName(),
                                        event.getTableName(),
                                        event.getColumnsToUpdate(),
                                        event.getColumnsForCondition());
                    }
                    else
                    {
                        dao.save(event.getModel(),
                                        event.getDatabaseName(),
                                        event.getTableName(),
                                        event.getColumnsForCondition());
                    }
                    eventQueue.poll();
                }
                catch(Throwable e)
                {
                    System.err.println("Problem forwarding task: " + e.getMessage());
                }
            }*/
            else if(event.getOperationType() == DatabaseUpdateEventType.DELETE)
            {
                try
                {
                    dao.delete(event.getModel(), event.getColumnsForCondition());
                    eventQueue.poll();
                }
                catch(Throwable e)
                {
                    System.err.println("Problem forwarding task: " + e.getMessage());
                }
            }
        }
    }


    public void shutdown()
    {
        running = false;
        if(forwardThread != null)
        {
            forwardThread.interrupt();
        }
    }


    public DAO getDao()
    {
        return dao;
    }
}
