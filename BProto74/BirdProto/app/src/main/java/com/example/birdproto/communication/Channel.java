package com.example.birdproto.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Channel implements Runnable
{
	private DatagramSocket socket;
	private boolean running;

	private OnSocketListener onSocketListener;

	public Channel(OnSocketListener onSocketListener)
	{
		this.onSocketListener = onSocketListener;
	}
	
	public void bind(int port) throws SocketException
	{
		socket = new DatagramSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop()
	{
		running = false;
		socket.close();
	}

	@Override
	public void run()
	{
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		running = true;
		while(running)
		{
			try
			{
				socket.receive(packet);
				
				String msg = new String(buffer, 0, packet.getLength());

				//byte[] b = Arrays.copyOfRange(buffer, 0, packet.getLength() -1);

				//byte[] a = new byte[packet.getLength()];
				//a = buffer;
				// System.out.println(msg);

				if(null != onSocketListener)
					onSocketListener.onReceived(msg);
			} 
			catch (IOException e)
			{
				break;
			}
		}
	}

	public void sendTo(final InetSocketAddress address, final byte[] msg)
	{
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{

				DatagramPacket packet = new DatagramPacket(msg, msg.length);
				packet.setSocketAddress(address);

				try
				{
					socket.send(packet);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
