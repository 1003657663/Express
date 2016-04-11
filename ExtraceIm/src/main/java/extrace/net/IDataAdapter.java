package extrace.net;


public interface IDataAdapter<T> {
	public T getData();
	public void setData(T data);
	public void notifyDataSetChanged();			
}
